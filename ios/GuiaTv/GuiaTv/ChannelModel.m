//
//  ChannelModel.m
//  GuiaTv
//
//  Created by Jordi Coscolla on 21/02/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import "ChannelModel.h"
#import "ProgramacioParser.h"

@implementation ChannelModel

@synthesize channelId = _channelId;
@synthesize channelDesc = _channelDesc;
@synthesize programas = _programas;

- (ChannelModel*) initChannelWithId: (NSString *)channelId AndDesc:(NSString*) channelDesc
{
    self = [self init];
    self.channelId  = channelId;
    self.channelDesc= channelDesc;
    
    return self;
}

- (ProgramacioModel*) getProgramas
{
    if( _programas == nil)
    {
        [self fetchProgramas];
    }
    
    return _programas;
}

- (void) fetchProgramas
{
    ProgramacioParser *parser = [[ProgramacioParser alloc] init];
    _programas = [parser parse: self.channelId];
}

@end
