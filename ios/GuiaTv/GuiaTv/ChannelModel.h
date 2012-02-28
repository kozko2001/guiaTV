//
//  ChannelModel.h
//  GuiaTv
//
//  Created by Jordi Coscolla on 21/02/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
@class ProgramaModel;
@class ProgramacioModel;

@interface ChannelModel : NSObject{
    
    @private 
    NSString *_channelId;
    NSString *_channelDesc;
    
}

- (ChannelModel*) initChannelWithId: (NSString *)channelId AndDesc:(NSString*) channelDesc;
- (ProgramacioModel*) getProgramas;

@property (retain, nonatomic) NSString *channelId;
@property (retain, nonatomic) NSString *channelDesc;
@property (retain, nonatomic) ProgramacioModel  *programas;
@end
